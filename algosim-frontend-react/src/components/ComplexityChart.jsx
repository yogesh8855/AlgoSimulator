import { useDarkMode } from '../context/DarkModeContext';

const sortingComplexity = {
  bubble: { name: 'Bubble Sort', best: 'O(n)', average: 'O(n²)', worst: 'O(n²)', space: 'O(1)' },
  selection: { name: 'Selection Sort', best: 'O(n²)', average: 'O(n²)', worst: 'O(n²)', space: 'O(1)' },
  insertion: { name: 'Insertion Sort', best: 'O(n)', average: 'O(n²)', worst: 'O(n²)', space: 'O(1)' },
  quick: { name: 'Quick Sort', best: 'O(n log n)', average: 'O(n log n)', worst: 'O(n²)', space: 'O(log n)' },
  merge: { name: 'Merge Sort', best: 'O(n log n)', average: 'O(n log n)', worst: 'O(n log n)', space: 'O(n)' },
};

export default function ComplexityChart({ algorithm }) {
  const { darkMode } = useDarkMode();
  const algoData = sortingComplexity[algorithm];

  if (!algoData) return null;

  return (
    <div className={`p-6 rounded-xl border-2 ${darkMode ? 'bg-gray-800 border-gray-700' : 'bg-white border-gray-200'}`}>
      <h3 className={`text-xl font-bold mb-6 ${darkMode ? 'text-white' : 'text-gray-900'}`}>
        {algoData.name} - Complexity Analysis
      </h3>

      {/* Complexity Table */}
      <div className={`grid grid-cols-2 md:grid-cols-4 gap-4 mb-6`}>
        <div className={`p-4 rounded-lg ${darkMode ? 'bg-gray-700' : 'bg-gray-50'}`}>
          <div className={`text-sm font-medium ${darkMode ? 'text-gray-400' : 'text-gray-600'}`}>Best Case</div>
          <div className={`text-lg font-bold ${darkMode ? 'text-green-400' : 'text-green-600'}`}>{algoData.best}</div>
        </div>
        <div className={`p-4 rounded-lg ${darkMode ? 'bg-gray-700' : 'bg-gray-50'}`}>
          <div className={`text-sm font-medium ${darkMode ? 'text-gray-400' : 'text-gray-600'}`}>Average Case</div>
          <div className={`text-lg font-bold ${darkMode ? 'text-yellow-400' : 'text-yellow-600'}`}>{algoData.average}</div>
        </div>
        <div className={`p-4 rounded-lg ${darkMode ? 'bg-gray-700' : 'bg-gray-50'}`}>
          <div className={`text-sm font-medium ${darkMode ? 'text-gray-400' : 'text-gray-600'}`}>Worst Case</div>
          <div className={`text-lg font-bold ${darkMode ? 'text-red-400' : 'text-red-600'}`}>{algoData.worst}</div>
        </div>
        <div className={`p-4 rounded-lg ${darkMode ? 'bg-gray-700' : 'bg-gray-50'}`}>
          <div className={`text-sm font-medium ${darkMode ? 'text-gray-400' : 'text-gray-600'}`}>Space Complexity</div>
          <div className={`text-lg font-bold ${darkMode ? 'text-purple-400' : 'text-purple-600'}`}>{algoData.space}</div>
        </div>
      </div>

      {/* Description */}
      <div className={`p-4 rounded-lg ${darkMode ? 'bg-gray-700' : 'bg-gray-50'}`}>
        <h4 className={`font-semibold mb-2 ${darkMode ? 'text-white' : 'text-gray-900'}`}>Algorithm Description</h4>
        <p className={`text-sm ${darkMode ? 'text-gray-300' : 'text-gray-600'}`}>
          {algoData.name} has a time complexity of {algoData.average} on average and requires {algoData.space} of additional space.
        </p>
      </div>
    </div>
  );
}
